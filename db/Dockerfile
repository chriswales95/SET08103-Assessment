# Use the latest MySQL image
FROM mysql

# Set the working directory
WORKDIR /tmp

# Copy all the files to the working directory of the container
COPY *.sql /tmp/

# Copy the world.sql file to docker-entrypoint-initdb.d.
COPY world.sql /docker-entrypoint-initdb.d

# Set the root password
ENV MYSQL_ROOT_PASSWORD example