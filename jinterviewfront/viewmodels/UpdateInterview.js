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
        selectStatus: '',
        status: ['待面试', , 'OFFER', '等通知', '凉凉', '复试', '取消面试']
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
                    this.time = interview.time;
                    this.note = interview.note;
                    this.selectStatus = interview.status;
                })
                .catch(function(error) {
                    console.error(error);
                });
        },


        updateinterview() {
            axios.post('/interview/update', {
                    interviewId: this.interviewId,
                    company: this.company,
                    address: this.address,
                    stars: this.stars,
                    time: this.time,
                    note: this.note,
                    status: this.selectStatus,
                })
                .then(function(response) {
                    console.log(response);
                    alert('修改成功');
                })
                .catch(function(error) {
                    console.log(error);
                    alert('修改失败');
                });
        }
    }
});