const Student = require('../../models/student')
const check = require('../../functions/checkIfExist')
const verify = require('../../functions/verifyUser')

module.exports = async(req, res, next) => {
    if(! await check(req.body.studentEmail, req.body.userType)){
        console.log("User not found!!!")
        res.status(400).send(false)
    }
    else{
        if(!await verify(req.body)){
            console.log("Wrong Credentials...")
            res.status(400).send(false)
        }
        else{
            const student = await Student.findOne({studentEmail: req.body.studentEmail, studentPassword: req.body.studentPassword})
            const token = student.generateAuthToken()
            console.log('token generated: ' + token)
            res.header('user-auth-token', token).status(200).send(true)
        }
    }
}