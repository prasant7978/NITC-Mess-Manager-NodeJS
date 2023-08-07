const mongoose = require('mongoose')
const config = require('config')
const jwt = require('jsonwebtoken')

const adminSchema = new mongoose.Schema({
    adminName: {type: 'string', required: true},
    adminEmail: {type: 'string', required: true},
    adminPassword: {type: 'string', required: true},
    userType: {type: 'string', required: true},
})

adminSchema.methods.generateAuthToken = function(){
    const jwtGenerated = jwt.sign({_id: this._id, email: this.email}, config.get('PRIVATE_KEY'))
    return jwtGenerated
}

module.exports = mongoose.model('admin', adminSchema)