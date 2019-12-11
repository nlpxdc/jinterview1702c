var app = new Vue({
    el: '#app',
    data: {
        loading: false,
        finished: false,
        keyword: '',
        onlyme: false,
        interviews: []
    },
    computed: {
        lastInterview() {
            return this.interviews.length === 0 ? [] : this.interviews[this.interviews.length - 1];
        },
        time(){
            return this.interviews.length === 0 ? '' : this.lastInterview.timestamp;
        }
    },
    mounted() {
        console.log('view mounted');
        this.searchInterview();
    },
    methods: {
        onLoad() {
            console.log('on load');
            this.time = this.lastInterview.timestamp;
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