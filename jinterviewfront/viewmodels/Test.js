var app = new Vue({
    el: '#app',
    data: {
        information:{"住址":"","公民身份号码":"","出生":"","姓名":"","性别":"","民族":""},     
         list: [],
        loading: false,
        finished: false,
        files:{"content":"",id:0}
    },
    methods:{
        afterRead(file){
            this.files.content=file.content
            console.log(this.files)
            this.files.id=1
            console.log(this.files)
            axios.post("http://localhost:8080/upload/photos",this.files)
            .then(res => {
                this.information.住址=res.data.words_result.住址.words
                this.information.公民身份号码=res.data.words_result.公民身份号码.words
                this.information.出生=res.data.words_result.出生.words
                this.information.姓名=res.data.words_result.姓名.words
                this.information.性别=res.data.words_result.性别.words
                this.information.民族=res.data.words_result.民族.words
                console.log(this.information)
            })
            .catch(err => {
                console.error(err); 
            })
        },
    }
});