const Contractor = require('../../models/contractor')

module.exports = async (req, res, next) => {
    try{
        const result = await Contractor.deleteOne({_id: req.query.contractorId})

        if(result.deletedCount == 1){
            console.log("successfully deleted the contractor")
            res.status(200).send(true)
        }
        else{
            console.log("no contractor deleted")
            res.status(400).send(false)
        }
    }catch(err){
        console.log("Erro: " + err)
        res.status(400).send(false)
    }
}