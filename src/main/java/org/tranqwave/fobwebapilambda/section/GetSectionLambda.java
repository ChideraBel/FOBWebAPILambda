package org.tranqwave.fobwebapilambda.section;

import com.amazonaws.services.lambda.runtime.Context;
import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.GetSectionRequest;
import model.GetSectionResponse;
import model.Section;

import static utils.ConstantUtils.toSection;

public class GetSectionLambda {
    private final SectionDao sectionDao;

    public GetSectionLambda(SectionDao sectionDao) {this.sectionDao = sectionDao;}

    public GetSectionResponse getSection(GetSectionRequest request, Context context) {
        final DynamoDBSection dynamoDBSection =  sectionDao.getSection(request.getSectionId());

        final Section section = toSection(dynamoDBSection);

        //Log action
        context.getLogger().log(String.format("Retrieved section with id: %d from FOBSectionTable", section.getSection_id()));

        return new GetSectionResponse(section);
    }
}
