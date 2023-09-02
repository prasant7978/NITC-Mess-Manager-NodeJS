const Student = require('../../models/student')

module.exports = async (req, res) => {
    try{
        const student = await Student.findOne({_id: req.query.studentId}).exec()
        if(student){
            console.log("student found by id")
            res.status(200).send(student)
        }
        else{
            console.log("student not found by id")
            res.status(400).send(null)
        }
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(null)
    }
}