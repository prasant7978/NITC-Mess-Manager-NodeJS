const Contractor = require('../../models/contractor')
const check = require('../../functions/checkIfExist')

module.exports = async (req, res) => {
    const isPresent = await check(req.body.cotractorEmail, req.body.userType)
    if(!isPresent){
        const contractor = new Contractor({
            contractorName: req.body.contractorName,
            contractorEmail: req.body.contractorEmail,
            contractorPassword: req.body.contractorPassword,
            messName: req.body.messName,
            foodType: req.body.foodType,
            capacity: req.body.capacity,
            availability: req.body.availability,
            costPerDay: req.body.costPerDay,
            userType: req.body.userType
        })

        try{
            await contractor.save().then(function(contractorSaved){
                if(!contractorSaved){
                    console.log("contractor not created.....")
                    res.status(400).send(false)
                }
                else{
                    console.log("contractor created.....")
                    res.status(200).send(true)
                }
            })
        }catch(err){
            console.log("Error: ", err)
            res.status(400).send(false)
        }
    }
    else{
        console.log("Contractor already exist...")
        res.status(404).send(false)
    }
}