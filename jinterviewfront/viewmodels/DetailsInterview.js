var app = new Vue({
    el: '#app',
    data: {
        interview: {},
        Status: [],
        stars: 0,
        // audio_url: "",
        form: {},
        images: []

    },
    methods: {

        getinterviewByid(interviewId) {
            axios.get("http://localhost:8080/interview/getById", { params: { interviewId: interviewId } })
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


        this.getinterviewByid(1);

    },
});