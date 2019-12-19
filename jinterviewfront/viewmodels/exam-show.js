var app = new Vue({
    el: '#app',
    data() {
        return {
            examId: '',
            exam: '',
            images: [],
            show: false,
            startPosition:0,
            examIndex:[]

        };
    },
    mounted() {
        var url = new URL(location.href);
        this.examId = url.searchParams.get("examId");
        if (!this.examId) {
            alert('examId 不存在');
            return;
        }
        this.getExamById();
    },
    methods: {
        getExamById() {
             //识别ID
             this.examIndex.push(this.examId)
            axios.get("exam/getExamById", {
                params: {
                    examId: this.examId
                }
            })
                .then(res => {
                    console.log(res.data);
                    this.exam = res.data;
                    this.images = this.exam.examPhotoUrls;
                    
                    //For循环传入数组examIndex里
                    res.data.examPhotoUrls.forEach(element => {
                        this.examIndex.push(element)
                    });
                    //识别图片方法
                    this.distinguish();
                })
                .catch(err => {
                    console.error(err);
                });
        },
        onChange(index) {
            console.log(index);
            this.startPosition = index;
            this.show=true;
        },

        
        distinguish(){
            axios.post("/interview/distinguish",this.examIndex).then(res=>{
                console.log(res)
                this.exam.content=res.data
            })
        }
    }
});