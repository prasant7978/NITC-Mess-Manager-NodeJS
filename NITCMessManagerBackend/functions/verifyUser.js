const Student = require('../models/student')
const Contractor = require('../models/contractor')
const Admin = require('../models/admin')

module.exports = async(user) => {
    if(user.userType === 'Admin'){
        const admin = await Admin.findOne({adminEmail: user.adminEmail, adminPassword: user.adminPassword}).exec()
        if(!admin)
            return null
        else
            return admin.generateAuthToken()
    }
    else if(user.userType === 'contractor'){
        const contractor = await Contractor.findOne({contractorEmail: user.email, contractorPassword: user.password}).exec()
        if(!contractor)
            return null
        else
            return contractor.generateAuthToken()
    }
    else{
        const student = await Student.findOne({studentEmail: user.studentEmail, studentPassword: user.studentPassword}).exec()
        if(!student)
            return null
        else
            return student.generateAuthToken()
    }
}