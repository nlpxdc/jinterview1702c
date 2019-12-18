var app = new Vue({
    el: '#app',
    data: {},
    mounted() {
        console.log('view mounted');
        const token = localStorage['jinterviewToken'];
        if (token) {
            const status = localStorage['studentStatus'];
            if (status == 1) {
                location.href = 'interview-index.html';
            } else {
                location.href = 'student-activate.html';
            }
        } else {
            location.href = 'ilogin.html';
        }

    }
});