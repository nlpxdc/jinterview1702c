var app = new Vue({
    el: '#app',
    data: {
        interview: {},
        Status: [],
        stars: 0,
        // audio_url: "",
        form: {},
        images: [],
        interviewId: 0

    },
    methods: {

        getinterviewByid(interviewId) {
            axios.get("/interview/getById", {
                    params: { interviewId: this.interviewId }
                })
                .then(res => {

                    console.log(res)
                    this.interview = res.data;
                    this.stars = res.data.stars;
                    this.form = res.data;
                    this.images = res.data.examphotoUrls;

                })
                .catch(err => {
                    console.error(err);
                })
        },

    },
    mounted() {

        this.interviewId = 1;
        this.getinterviewByid();

    },
});