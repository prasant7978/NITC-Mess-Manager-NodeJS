const Admin = require('../models/admin')

module.exports = async(id) => {
    const admin  = await Admin.findOne({_id: id}).exec()
    if(admin)
        return admin
    else
        return null
}