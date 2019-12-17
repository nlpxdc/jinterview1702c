var app = new Vue({
    el: '#app',
    data() {
        return {
            examId: '',
            company: '',
            content: '',
            likes: 0,
            time: '',
            realname: '',
            examphotoUrls: []
        };
    },
    mounted() {
        var url = new URL(location.href);
        this.examId = url.searchParams.get("examId");
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
                    this.company = res.data.company;
                    this.time = res.data.time;
                    this.content = res.data.content;
                    this.examId = res.data.examId;
                    this.likes = res.data.likes;
                    this.realname = res.data.realname;
                    this.examphotoUrls = res.data.examPhotolist;
                })
                .catch(err => {
                    console.error(err);
                });
        }
    }
});