package org.tranqwave.fobwebapilambda.section;

import com.amazonaws.services.lambda.runtime.Context;
import dao.SectionDao;
import dao.dbModels.DynamoDBSection;
import model.CreateSectionRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.SUCCESS;

public class CreateSectionLambda {
    private final SectionDao sectionDao;

    public CreateSectionLambda(SectionDao sectionDao) {
        this.sectionDao = sectionDao;
    }

    public ResponseMessage createSection(CreateSectionRequest request) {

        int sectionId = sectionDao.getNextSequence();

        final DynamoDBSection section = new DynamoDBSection(sectionId, request.getName(), request.getContent(), request.getDescription() );
        sectionDao.save(section);

        return new ResponseMessage(SUCCESS, "Section successfully created");
    }
}
