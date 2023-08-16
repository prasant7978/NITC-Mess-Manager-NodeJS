const getContractorByEmail = require('../../functions/getContractorByEmail')

module.exports = async(req, res) => {
    const contractor = await getContractorByEmail(req.id)
    if(contractor)
        res.status(200).send(contractor)
    else
        res.status(400).send(null)
}