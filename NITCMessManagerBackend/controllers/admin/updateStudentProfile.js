const Student = require('../../models/student')

module.exports = async (req, res) => {
    try{
        await Student.findOneAndUpdate(
            {
                _id: req.body.studentId
            },{
                studentName: req.body.studentName,
                studentEmail: req.body.studentEmail,
                studentPassword: req.body.studentPassword,
                studentRollNo: req.body.studentRollNo
            }
        )

        console.log("student updated...")
        res.status(200).send(true)
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(false);
    }
}