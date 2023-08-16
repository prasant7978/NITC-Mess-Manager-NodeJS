const Contractor = require('../models/contractor')

module.exports = async(id) => {
    const contractor = await Contractor.findOne({_id: id}).exec()
    if(contractor)
        return contractor
    else
        return null
}