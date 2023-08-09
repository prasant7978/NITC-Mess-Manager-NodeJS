const check = require('../../functions/checkIfExist')
const verify = require('../../functions/verifyUser')

module.exports = async(req, res) => {
    const isPresent = await check(req.body.studentEmail, req.body.userType)
    if(!isPresent){
        console.log("User not found!!!")
        res.status(400).send(false)
    }
    else{
        const token = await verify(req.body)
        if(token == null){
            console.log("Wrong Credentials...")
            res.status(400).send(false)
        }
        else{
            console.log('token generated: ' + token)
            res.header('user-auth-token', token).status(200).send(true)
        }
    }
}