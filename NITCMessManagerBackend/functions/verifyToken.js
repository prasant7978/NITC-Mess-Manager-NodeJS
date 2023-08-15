const jwt = require('jsonwebtoken')
const config = require('config')

module.exports = async(req, res) => {
    const jwtToken = req.headers['user-auth-token']
    console.log("jwt = " + jwtToken);
    if(!jwtToken) {
        console.log("token not verified");
        res.status(400).send(false);
    }
    else{
        try{
            const user = jwt.verify(jwtToken, config.get('PRIVATE_KEY'))
            if(!user){
                console.log("User not found");
                res.status(400).send(false)
            }
            else{
                console.log("User found");
                res.status(200).send(true)
            }
        }catch(err){
            console.log(err);
            res.status(500).send(false)
        }
    }
}