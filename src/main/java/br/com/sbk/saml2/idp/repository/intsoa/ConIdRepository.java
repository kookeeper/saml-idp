package br.com.sbk.saml2.idp.repository.intsoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sbk.saml2.idp.entity.intsoa.ConId;

@Repository
public interface ConIdRepository extends JpaRepository<ConId, String> {

	ConId findByUserName(@Param("UserName") String userName);
}
