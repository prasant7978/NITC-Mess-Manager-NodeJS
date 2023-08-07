const mongoose = require('mongoose')
const config = require('config')
const jwt = require('jsonwebtoken')

const studentSchema = new mongoose.Schema({
    studentName: {type: 'string', required: true},
    studentEmail: {type: 'string', required: true},
    studentPassword: {type: 'string', required: true},
    studentRollNo: {type: 'string', required: true},
    messEnrolled: {type: 'string'},
    messBill: {type: 'number', default: 0},
    paymentStatus: {type: 'string', default: 'paid'},
    userType: {type: 'string', required: true},
})

studentSchema.methods.generateAuthToken = function(){
    const jwtGenerated = jwt.sign({_id: this._id, email: this.email}, config.get('PRIVATE_KEY'))
    return jwtGenerated
}

module.exports = mongoose.model('student', studentSchema)