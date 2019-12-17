var app = new Vue({
    el: '#app',
    data() {
        return {
            examId: '',
            exam: ''
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
                })
                .catch(err => {
                    console.error(err);
                });
        }
    }
});