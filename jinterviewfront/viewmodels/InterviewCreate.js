var app = new Vue({
    el: '#app',
    data: {
        company: '',
        address: '',
        interviewTime: ''
    },
    mounted() {
        console.log('view mounted');
    },
    methods: {
        handleCreateClick() {
            console.log('create click');
            this.createInterview();
        },
        createInterview() {
            axios.post('/interview/create', {
                company: this.company,
                address: this.address,
                time: this.interviewTime.getTime()
            })
                .then(function (response) {
                    console.log(response);
                    alert('创建成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert('创建失败');
                });
        }
    }
});