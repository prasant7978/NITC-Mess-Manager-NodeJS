const Student = require('../models/student')
const Contractor = require('../models/contractor')
const Admin = require('../models/admin')

module.exports = async(email, userType) => {
    if(userType == 'Admin'){
        const admin = await Admin.findOne({adminEmail: email}).exec();
        if(admin)
            return true
        else
            return false
    }
    else if(userType == 'contractor'){
        const contractor = await Contractor.findOne({contractorEmail: email}).exec();
        if(contractor)
            return true
        else
            return false
    }
    else{
        const student = await Student.findOne({studentEmail: email}).exec();
        if(student)
            return true
        else
            return false
    }
}