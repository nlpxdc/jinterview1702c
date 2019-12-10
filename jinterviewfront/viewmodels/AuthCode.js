var app = new Vue({
    el: '#app',
    data: {
        code: ''
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.code = url.searchParams.get("code");

    },
    methods: {
        handleLoginClick() {
            console.log('login click');
            this.studentAutoLogin();
        },
        studentAutoLogin() {
            axios.get('/student/autoRegisterLogin', {
                params: {
                    code: this.code
                }
            })
                .then(function (response) {
                    console.log(response);
                    var jinterviewToken = response.data;
                    localStorage['jinterviewToken'] = jinterviewToken;
                    alert('登陆成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert('登陆失败');
                });
        }
    }
});