const getContractorByEmail = require('../../functions/getContractorByEmail')

module.exports = async(req, res) => {
    const result = await getContractorByEmail(req.id)
    
    if(result){
        const contractor = {
            contractorName: result.contractorName,
            contractorEmail: result.contractorEmail,
            messName: result.messName,
            foodType: result.foodType,
            capacity: result.capacity,
            availability: result.availability,
            costPerDay: result.costPerDay,
            totalDue: result.totalDue,
            userType: result.userType,
            totalEnrolled: result.studentEnrolled.length
        }
        res.status(200).send(contractor)
    }
    else
        res.status(400).send(null)
}