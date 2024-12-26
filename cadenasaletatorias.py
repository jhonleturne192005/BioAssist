import string
import random
import uuid

print(uuid.uuid4())


number_of_strings = 5
length_of_string = 8


"".join(
    random.SystemRandom().choice(string.ascii_letters + string.digits)
    for _ in range(length_of_string)
)


