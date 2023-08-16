const getAdminByEmail = require('../../functions/getAdminByEmail')

module.exports = async(req, res) => {
    const admin = await getAdminByEmail(req.id)
    if(admin)
        res.status(200).send(admin)
    else
        res.status(400).send(null)
}