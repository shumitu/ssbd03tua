import React from 'react';
import {MDBCard, MDBCardBody, MDBCardImage, MDBCardText, MDBCardTitle, MDBCol,} from 'mdbreact';
import {useTranslation} from 'react-i18next';

const ContactCard = () => {
  const { t } = useTranslation(['contact_card']);
  return (
    <div className="contact-card">
      <MDBCol md="4">
        <MDBCard wide cascade>
          <MDBCardImage
            hover
            overlay="white-light"
            className="card-img-top"
            src={require('../../../resources/images/contact_card.jpg')}
            alt="man"
          />
          <MDBCardBody cascade className="text-center">
            <MDBCardTitle className="card-title">
              <strong>{t('title')}</strong>
            </MDBCardTitle>
            <MDBCardText>
              {t('body')}
            </MDBCardText>
            {t('email')}
          </MDBCardBody>
        </MDBCard>
      </MDBCol>
    </div>

  );
};

export default ContactCard;
