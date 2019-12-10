var app = new Vue({
    el: '#app',
    data: {},
    methods:{
        handleLoginClick(){
            console.log('login click');
            location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0c14a6dfeab19166&redirect_uri=http%3A%2F%2F192.168.137.1%2Fstudent%2FautoRegisterLogin&response_type=code&scope=snsapi_userinfo#wechat_redirect';
        }
    }
});