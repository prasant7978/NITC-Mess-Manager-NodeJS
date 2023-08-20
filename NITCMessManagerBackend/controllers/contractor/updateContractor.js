const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    try{
        await Contractor.findOneAndUpdate({_id:req.id},{
            contractorName: req.body.contractorName,
            messName: req.body.messName,
            foodType: req.body.foodType,
            capacity: req.body.capacity,
            availability: req.body.availability,
            costPerDay: req.body.costPerDay
        })
        
        console.log("contractor updated...")
        res.status(200).send(true)
    }catch(err){
        console.log("Error: " + err.message)
        res.status(400).send(false);
    }
}