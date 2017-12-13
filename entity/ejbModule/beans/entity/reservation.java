/**
 * 
 */
package beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * @author Junzi
 *
 */
@Entity
@Table(name = "reservation")
public class reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
}
