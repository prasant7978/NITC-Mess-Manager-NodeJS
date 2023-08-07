const Student = require('../../models/student')
const check = require('../../functions/checkIfExist')

module.exports = async(req, res, next) => {
    const isPresent = await check(req.body.studentEmail, req.body.userType)
    if(!isPresent){
        const student = new Student({
            studentEmail: req.body.studentEmail,
            studentPassword: req.body.studentPassword,
            studentName: req.body.studentName,
            studentRollNo: req.body.studentRollNo,
            userType: "Student"
        })
        console.log("signup details: ", req.body)
        try{
            await student.save().then(function(studentSaved){
                if(!studentSaved){
                    console.log("student not created.....")
                    res.status(400).send(false)
                }
                else{
                    const token = student.generateAuthToken()
                    console.log("token generated: " + token)
                    res.header('user-auth-token', token).status(200).send(true)
                }
            })
        }catch(err){
            console.log("Error: ", err)
            res.status(400).send(false)
        }
    }
    else{
        console.log("User already exist...")
        res.status(404).send(false)
    }
}