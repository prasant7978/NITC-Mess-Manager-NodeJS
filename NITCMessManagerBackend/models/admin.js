const mongoose = require('mongoose')

const adminSchema = new mongoose.Schema({
    adminName: {type: 'string', required: true},
    adminEmail: {type: 'string', required: true},
    adminPassword: {type: 'string', required: true},
    userType: {type: 'string', required: true},
})

module.exports = mongoose.model('admin', adminSchema)