var app = new Vue({
    el: '#app',
    data() {
        return {
            examId: '',
            exam: '',
            images: [],
            show: false,
            startPosition:0,
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
            axios.get("exam/getExamById", {
                params: {
                    examId: this.examId
                }
            })
                .then(res => {
                    console.log(res.data);
                    this.exam = res.data;
                    this.images = this.exam.examPhotoUrls;
                })
                .catch(err => {
                    console.error(err);
                });
        },
        onChange(index) {
            console.log(index);
            this.startPosition = index;
            this.show=true;
        }
    }
});