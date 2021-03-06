var app = new Vue({
    el: '#app',
    data: {
        interview: '',
        images: [],
        interviewId: '',
        status: ['待面试', 'OFFER', '等通知', '凉凉', '复试', '取消面试'],
        active: 0,
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.interviewId = url.searchParams.get("interviewId");
        if (!this.interviewId) {
            alert('interviewId 不存在');
            return;
        }
        this.getInterviewById();
    },
    methods: {
        getInterviewById() {
            axios.get("/interview/getById", {
                params: {
                    interviewId: this.interviewId,
                    nonce: Date.now()
                }
            })
                .then(res => {
                    console.log(res)
                    this.interview = res.data;
                })
                .catch(err => {
                    console.error(err);
                    alert(err.response.data.message);
                })
        },
        handleExamShowTouch() {
            console.log('exam show click');
            location.href = 'exam-show.html?examId=' + this.interview.examId;
        },
        handleUpdateTouch() {
            console.log('update touch');
            location.href = 'interview-update.html?interviewId=' + this.interviewId;
        },
        handleAddressTouch() {
            console.log('address touch');
            location.href = 'interview-address.html?address=' + this.interview.address;
        },
        handleBackTouch() {
            console.log('back touch');
            history.back();
        }
    }
});