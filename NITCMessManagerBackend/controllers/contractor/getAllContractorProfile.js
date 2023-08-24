const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    const contractor = await Contractor.find({}).exec()
    if(contractor)
        res.status(200).send(contractor)
    else
        res.status(400).send(null)
}