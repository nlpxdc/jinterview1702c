var app = new Vue({
    el: '#app',
    data: {},
    mounted() {
        console.log('view mounted');
        const token = localStorage['jinterviewToken'];
        const expireDate = localStorage['expireDate'];
        const now = Date.now();
        if (!token || now > expireDate) {
            location.href = 'ilogin.html';
        } else {
            const status = localStorage['studentStatus'];
            if (status == 1) {
                location.href = 'interview-index.html';
            } else {
                location.href = 'student-activate.html';
            }
        }
    }
});