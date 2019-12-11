var app = new Vue({
    el: '#app',
    data: {
        loading: false,
        finished: false,
        keyword: '',
        onlyme: false,
        time: '',
        interviews: []
    },
    mounted() {
        console.log('view mounted');
    },
    methods: {
        onLoad() {
            console.log('on load');
            const lastInterview = this.interviews.length ? this.interviews[this.interviews.length - 1] : '';
            this.time = lastInterview ? lastInterview.timestamp : '';
            this.searchInterview();
        },
        searchInterview() {
            axios.get('/interview/search', {
                params: {
                    keyword: this.keyword,
                    onlyme: this.onlyme,
                    time: this.time
                }
            })
                .then(function (response) {
                    console.log(response);
                    var newInterviews = response.data;
                    app.interviews = app.interviews.concat(newInterviews);
                    app.loading = false;
                    app.finished = newInterviews < 10;
                })
                .catch(function (error) {
                    console.error(error);
                });
        },
        handleSearchClick() {
            console.log('search click');
        }
    }
});