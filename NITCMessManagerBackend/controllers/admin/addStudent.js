const Student = require('../../models/student')
const check = require('../../functions/checkIfExist')

module.exports = async (req, res) => {
    const isPresent = await check(req.body.studentEmail, req.body.userType)
    if(!isPresent){
        const student = new Student({
            studentEmail: req.body.studentEmail,
            studentPassword: req.body.studentPassword,
            studentName: req.body.studentName,
            studentRollNo: req.body.studentRollNo,
            messEnrolled: req.body.messEnrolled,
            userType: "Student"
        })

        try{
            await student.save().then(function(studentSaved){
                if(!studentSaved){
                    console.log("student not created.....")
                    res.status(400).send(false)
                }
                else{
                    console.log("student created.....")
                    res.status(200).send(true)
                }
            })
        }catch(err){
            console.log("Error: ", err)
            res.status(400).send(false)
        }
    }
    else{
        console.log("Student already exist...")
        res.status(404).send(false)
    }
}