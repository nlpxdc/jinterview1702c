var app = new Vue({
    el: '#app',
    data: {
        stars: 0,
        message: '',
        interviewId: '',
        company: '',
        address: '',
        time: '',
        note: '',
        selectStatus: '',
        status: [
            {
                value: '0',
                label: 'OFFER'
            },
            {
                value: '1',
                label: '等通知'
            },
            {
                value: '2',
                label: '凉凉'
            },
            {
                value: '3',
                label: '取消面试'
            }
        ],
    },
    methods: {

        getInterviewById() {
            axios.get('http://localhost:80/interview/getById', {
                params: {
                    interviewId: this.interviewId
                }
            })
                .then(response => {
                    //console.log(response);
                    var interview = response.data
                    this.interviewId = interview.interviewId;
                    this.company = interview.company;
                    this.address = interview.address;
                    this.stars = interview.stars;
                    this.time = interview.time;
                    this.note = interview.note;
                    this.selectStatus = interview.status + '';
                })
                .catch(function (error) {
                    console.log(error);
                });
        },


        updateinterview() {
            axios.post('http://localhost:80/interview/update', {
                interviewId: this.interviewId,
                company: this.company,
                address: this.address,
                stars: this.stars,
                time: this.time,
                note: this.note,
                status: this.selectStatus,
            })
                .then(function (response) {
                    console.log(response);
                    alert('修改成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert('修改失败');
                });
        }
    },
    mounted() {

    }
});