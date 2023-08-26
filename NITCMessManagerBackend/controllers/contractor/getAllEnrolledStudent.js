const Student = require('../../models/student')

module.exports = async(req, res) => {
    try{
        const student =await Student.find({messEnrolled: req.query.messName}).exec()
        if(student)
            res.status(200).send(student)
        else
            res.status(400).send(null)
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(null)
    }
}