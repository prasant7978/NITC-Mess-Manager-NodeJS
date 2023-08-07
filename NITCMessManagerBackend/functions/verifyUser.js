const Student = require('../models/student')
const Contractor = require('../models/contractor')
const Admin = require('../models/admin')

module.exports = async(user) => {
    if(user.userType === 'admin'){
        const admin = await Admin.findOne({adminEmail: user.email, adminPassword: user.password}).exec()
        if(!admin)
            return false
        else
            return true
    }
    else if(user.userType === 'contractor'){
        const contractor = await Contractor.findOne({contractorEmail: user.email, contractorPassword: user.password}).exec()
        if(!contractor)
            return false
        else
            return true
    }
    else{
        const student = await Student.findOne({studentEmail: user.studentEmail, studentPassword: user.studentPassword}).exec()
        if(!student)
            return false
        else
            return true
    }
}