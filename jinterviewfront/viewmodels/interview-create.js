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
            if(!this.company){
                alert('公司名不能为空');
                return;
            }
            if(!this.address){
                alert('地址不能为空');
                return;
            }
            this.createInterview();
        },
        handleCancelTouch(){
            console.log('cancel touch');
            history.back();
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
                    history.back();
                })
                .catch(function (error) {
                    console.log(error);
                    alert('创建失败');
                });
        },
        onClickLeft() {

        },
        onClickRight() {

        }
    }
});