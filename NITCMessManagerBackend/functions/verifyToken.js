const jwt = require('jsonwebtoken')

module.exports = async(req, res, next) => {
    const jwtToken = req.header['user-auth-token']
    if(!jwtToken) {
        res.status(400).send(null);
    }
    else{
        try{
            const user = jwt.verify(jwtToken, config.get('PRIVATE_KEY'))
            if(!user){
                res.status(400).send(null);
            }
            else{
                req.id = user._id
                next()
            }
        }catch(err){
            res.status(500).send('server error...')
        }
    }
}