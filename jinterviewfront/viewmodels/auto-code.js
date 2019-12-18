var app = new Vue({
    el: '#app',
    data: {
        code: ''
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        console.log(url);
        this.code = url.searchParams.get("code");
        if (!this.code) {
            alert('code 不存在');
            return;
        }

        this.studentAutoLogin();

    },
    methods: {
        studentAutoLogin() {
            axios.get('/student/autoRegisterLogin', {
                params: {
                    code: this.code
                }
            })
                .then(function (response) {
                    console.log(response);
                    const tokenObj = response.data;
                    localStorage['jinterviewToken'] = tokenObj.token;
                    localStorage['expireDate'] = tokenObj.expireDate;
                    localStorage['studentStatus'] = tokenObj.status;
                    if (tokenObj.status == 1) {
                        location.href = 'interview-index.html';
                    } else {
                        location.href = 'student-activate.html';
                    }
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        }
    }
});