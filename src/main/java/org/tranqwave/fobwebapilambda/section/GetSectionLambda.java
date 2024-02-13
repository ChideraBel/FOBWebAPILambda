package org.tranqwave.fobwebapilambda.section;

import com.amazonaws.services.lambda.runtime.Context;
import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.GetSectionRequest;
import model.GetSectionResponse;
import model.Request;
import model.Section;

public class GetSectionLambda {
    private final SectionDao sectionDao;

    public GetSectionLambda(SectionDao sectionDao) {this.sectionDao = sectionDao;}

    public GetSectionResponse getSection(GetSectionRequest request, Context context) {
        final DynamoDBSection dynamoDBSection =  sectionDao.getSection(request.toString());

        final Section section = toSection(dynamoDBSection);

        //Log action
        context.getLogger().log(String.format("Retrieved section with id: %d from FOBSectionTable", section.getSection_id()));

        return new GetSectionResponse(section);
    }

    public Section toSection(DynamoDBSection dynamoDBSection) {
        return Section.builder()
                .section_id(dynamoDBSection.getSection_id())
                .name(dynamoDBSection.getSection_name())
                .content(dynamoDBSection.getSection_content())
                .description(dynamoDBSection.getSection_description())
                .build();
    }
}
