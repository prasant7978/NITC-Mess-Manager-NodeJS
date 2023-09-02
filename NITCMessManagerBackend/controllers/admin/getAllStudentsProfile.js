const Student = require('../../models/student')

module.exports = async(req, res) => {
    try{
        const students = await Student.find({}).exec()
        if(students){
            console.log("student list found")
            res.status(200).send(students)
        }
        else{
            console.log("student list not found")
            res.status(400).send(null)
        }
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(null);
    }
}