const Contractor = require('../../models/contractor')

module.exports = async (req, res) => {
    try{
        const contractor = await Contractor.findOne({_id: req.query.contractorId}).exec()
        if(contractor){
            console.log("contractor found by id")
            res.status(200).send(contractor)
        }
        else{
            console.log("contractor not found by id")
            res.status(400).send(null)
        }
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(null)
    }
}