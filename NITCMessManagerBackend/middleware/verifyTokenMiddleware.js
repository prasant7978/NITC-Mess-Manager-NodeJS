const jwt = require('jsonwebtoken')
const config = require('config')

module.exports = async(req, res, next) => {
    const jwtToken = req.headers['user-auth-token']
    console.log("jwt = " + jwtToken);
    if(!jwtToken) {
        console.log("token not found...");
        res.status(400).send(null);
    }
    else{
        try{
            const user = jwt.verify(jwtToken, config.get('PRIVATE_KEY'))
            if(!user){
                console.log("user not found...");
                res.status(400).send(null);
            }
            else{
                req.id = user._id
                req.usertype = user.userType
                next()
            }
        }catch(err){
            console.log(err);
            res.status(500).send('server error from backend...')
        }
    }
}