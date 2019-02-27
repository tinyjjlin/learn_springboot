/**
 * @author tiny lin
 * @date 2019/2/18
 */
@ConfigurationProperties("service")
public class HelloService {

    /**
     * A message for the service.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}