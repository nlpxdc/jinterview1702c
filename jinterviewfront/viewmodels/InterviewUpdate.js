var app = new Vue({
    el: '#app',
    data: {
        stars: 0,
        message: '',
        interviewId: '',
        company: '',
        address: '',
        time: '',
        note: ' ',
        selectStatus: ''
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
            axios.get('/interview/getById', {
                params: {
                    interviewId: this.interviewId
                }
            })
                .then(response => {
                    console.log(response);
                    var interview = response.data
                    this.interviewId = interview.interviewId;
                    this.company = interview.company;
                    this.address = interview.address;
                    this.stars = interview.stars;
                    this.time = new Date(interview.time);
                    this.note = interview.note;
                    this.selectStatus = interview.status.toString();
                })
                .catch(function (error) {
                    console.error(error);
                });
        },
        handleUpdateClick() {
            console.log('update click');
            this.updateInterview();
        },
        updateInterview() {
            axios.post('/interview/update', {
                interviewId: this.interviewId,
                stars: this.stars,
                time: this.time.getTime(),
                note: this.note,
                status: this.selectStatus,
            })
                .then(function (response) {
                    console.log(response);
                    alert('修改成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert(error.response.data.message);
                });
        },
        handleDeleteClick() {
            console.log('delete click');
            if (confirm('确认删除？')) {
                this.deleteInterview();
            }
        },
        deleteInterview() {
            axios.post('/interview/delete', this.interviewId)
                .then(function (response) {
                    console.log(response);
                    alert('删除成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert(error.response.data.message);
                });
        },
        onClickLeft() {

        },
        onClickRight() {

        }
    }
});