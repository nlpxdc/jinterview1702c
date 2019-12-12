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
        },
        createInterview() {
            axios.post('/interview/create', {
                company: this.company,
                address: this.address,
                time: this.interviewTime
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
});